export default Vue.component('set-project-manager', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%">
    <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="padding-bottom: 10px; display: flex; align-items: center; justify-content: center;">back</button>
        <div class="form-group" style="margin-top: 10px">    
            <select @change="onChange($event)" class="form-controll">
                <option key="-1" value="-1">---</option>
                <option v-for="(project, index) in projects" :value="index" :key="index">
                    {{project.name}}
                </option>
            </select>
            </div>
        <ul style="list-style-type: none;">
            <li v-for="(element, index) in users" :key="index">
                <button class="btn btn-outline-info" style="width: 100%; @click="show(element.id)">{{element.name}}</button>
                <button type="button" class="btn btn-outline-primary" style="width: 100%" @click="setPjm(element.id)">accept</button>
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
                this.users = await (await fetch('/api/program-manager/list-designers?iduser='+credential.id+'&tokenuser='+credential.token+'&idproject='+project.id)).json();
                this.$emit('load',false);
            }
        }
    }
});