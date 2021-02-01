export default Vue.component('remove-project-pjm', {
    template: `
    <div class='container'>
        <button @click="back()" type="button" class="btn btn-outline-primary">back</button>
        <select @change="onChange($event)">
            <option key="-1" value="-1">---</option>
            <option v-for="(project, index) in projects" :value="index" :key="index">
                {{project.name}}
            </option>
        </select>
        <div v-for="(element, index) in users" :key="index">
            <button @click="show(element.id)">{{element.name}}</button>
            <button type="button" class="btn btn-outline-primary" @click="choosePjm(element.id)">choose</button>
        </div>
    </div>
    `,
    data() {
        return {
            users: [],
            projects: [],
            indexProject: -1,
        }
    },
    created() {
        this.init();
    },
    methods: {
        async init() {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            this.projects = await (await fetch('/api/user/list-projects?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole=project-manager')).json();
            this.$emit('load',false);
        },
        async choosePjm(index) {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/project-manager/remove-project?iduser='+credential.id+'&tokenuser='+credential.token+'&idnextprojectmanager='+index+
                '&idproject='+this.projects[this.indexProject].id, {method: 'DELETE'})).text();
            await this.init();
            this.$emit('load',false);
            this.$emit('push', res);
        },
        back() {
            this.$router.go(-1);
        },
        show(id) {
            this.$router.push({path: '/user/'+id});
        },
        async onChange(event) {
            var index = event.target.value;
            if(index>=0) {
                this.$emit('load',true);
                this.indexProject = index;
                var project = this.projects[this.indexProject];
                var credential = JSON.parse(localStorage.getItem(key));
                this.users = await (await fetch('/api/project-manager/list-designers?iduser='+credential.id+'&tokenuser='+credential.token+'&idproject='+project.id)).json();
                this.$emit('load',false);
            }
        }
    }
});