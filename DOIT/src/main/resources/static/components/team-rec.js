export default Vue.component('team-rec', {
    template: `
    <div class='container'>
        <ul>
        <button @click="back()" type="button" class="btn btn-outline-primary">back</button>
            <li v-for="(project, index) in projects" :key="index">
                <div>
                    <button @click="go(index)">{{project.name}}</button>
                    <button type="button" class="btn btn-outline-primary" @click="manageRec(project)">{{project.team.open?"close":"open"}}</button>
                </div>
            </li>
        </ul>
    </div>
    `,
    data() {
        return {
            projects: []
        }
    },
    created() {
        this.init();
    },
    methods: {
        async init() {
            this.$emit('load', true);
            var credential = JSON.parse(localStorage.getItem(key));
            this.projects = await (await fetch('/api/user/list-projects?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole=program-manager')).json();
            this.$emit('load', false);
        },
        async manageRec(project) {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res;
            if(project.team.open)
                res = await (await fetch('/api/program-manager/close-registrations?iduser='+credential.id+'&tokenuser='+credential.token+'&idproject='+project.id, {method: 'PUT'})).text();
            else
                res = await (await fetch('/api/program-manager/open-registrations?iduser='+credential.id+'&tokenuser='+credential.token+'&idproject='+project.id, {method: 'PUT'})).text();
            this.$emit('load',false);
            alert(res);
            this.init();
        },
        go(i) {
            this.$router.push({path: '/project/'+this.projects[i].id});
        },
        back() {
            this.$router.go(-1);
        }
    }
});