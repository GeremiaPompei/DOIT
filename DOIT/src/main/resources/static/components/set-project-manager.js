export default Vue.component('set-project-manager', {
    template: `
    <div class='container'>
        <button @click="back()" type="button" class="btn btn-outline-primary">back</button>
        <select @change="onChange($event)">
            <option key="-1" value="-1">---</option>
            <option v-for="(project, index) in projects" :value="index" :key="index">
                {{project.name}}
            </option>
        </select>
        <ul>
            <li v-for="(element, index) in users" :key="index">
                <button @click="show(element.id)">{{element.name}}</button>
                <button type="button" class="btn btn-outline-primary" @click="setPjm(element.id)">accept</button>
            </li>
        </ul>
    </div>
    `,
    data() {
        return {
            users: [],
            projects: [],
            indexProject: -1
        }
    },
    created() {
        this.init();
    },
    methods: {
        async init() {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            this.projects = await (await fetch('/api/user/list-projects?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole=program-manager')).json();
            this.$emit('load',false);
        },
        async setPjm(index) {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/program-manager/set-project-manager?iduser='+credential.id+'&tokenuser='+credential.token+'&iddesigner='+index+'&idproject='+this.projects[this.indexProject].id, 
                {method: 'PUT'})).text();
            this.$emit('load',false);
            alert(res);
            this.init();
            this.users = [];
        },
        back() {
            this.$router.go(-1);
        },
        show(id) {
            this.$router.push({path: '/user/'+id});
        },
        async onChange(event) {
            this.$emit('load',true);
            this.indexProject = event.target.value;
            var project = this.projects[this.indexProject];
            var credential = JSON.parse(localStorage.getItem(key));
            this.users = await (await fetch('/api/program-manager/list-designers?iduser='+credential.id+'&tokenuser='+credential.token+'&idproject='+project.id)).json();
            this.$emit('load',false);
        }
    }
});