export default Vue.component('manage-project-state', {
    template: `
    <div class='container'>
        <ul>
        <button @click="back()" type="button" class="btn btn-outline-primary">back</button>
            <li v-for="(project, index) in projects" :key="index">
                <div>
                    <button type="button" class="btn btn-outline-primary" @click="downgrade(project.id)"><</button>
                    <button @click="go(index)">{{project.name+" : "+project.projectState.name}}</button>
                    <button type="button" class="btn btn-outline-primary" @click="upgrade(project.id)">></button>
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
            this.projects = await (await fetch('/api/project-manager/list-projects?iduser='+credential.id+'&tokenuser='+credential.token)).json();
            this.$emit('load', false);
        },
        async downgrade(id) {
            this.$emit('load', true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/project-manager/downgrade-state?iduser='+credential.id+'&tokenuser='+credential.token+'&idproject='+id, {method: "PUT"})).text();
            this.$emit('load', false);
            alert(res);
        },
        async upgrade(id) {
            this.$emit('load', true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/project-manager/upgrade-state?iduser='+credential.id+'&tokenuser='+credential.token+'&idproject='+id, {method: "PUT"})).text();
            this.$emit('load', false);
            alert(res);
        },
        go(i) {
            this.$router.push({path: '/project/'+this.projects[i].id});
        },
        back() {
            this.$router.go(-1);
        }
    }
});