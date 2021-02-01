export default Vue.component('remove-project-pjm', {
    template:
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%; flex-direction: column; align-items: center; justify-content: center;">
        <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="display: flex; align-items: center; justify-content: center;">back</button>
        <select @change="onChange($event)" class="form-control" style="margin-top: 10px">
            <option key="-1" value="-1">---</option>
            <option v-for="(project, index) in projects" :value="index" :key="index">
                {{project.name}}
            </option>
        </select>
        <div class="card border-info mb-3" style="margin-top: 10px" v-for="(element, index) in users" :key="index">
            <div class="card-header" @click="go(index)" style="text-align: center">
                <button class="btn btn-outline-info" style="width: 100%; margin-bottom: 5px"@click="show(element.id)">{{element.name}}</button>
            </div>
            <div class="card-body">
                <button type="button" class="btn btn-outline-primary" style="width: 100%;" @click="choosePjm(element.id)">choose</button>    
            </div>
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