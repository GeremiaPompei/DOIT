export default Vue.component('list-projects', {
    template: `
    <div class='container'>
        <ul>
        <button @click="back()" type="button" class="btn btn-outline-primary">back</button>
            <li v-for="(project, index) in projects" :key="index">
                <div @click="go(index)">
                    <button>{{project.name}}</button>
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
    async created() {
        var credential = JSON.parse(localStorage.getItem(key));
        this.projects = await (await fetch('/api/'+this.$route.params.role+'/list-projects?iduser='+credential.id+'&tokenuser='+credential.token)).json();
    },
    methods: {
        go(i) {
            this.$router.push({path: '/project/'+this.projects[i].id});
        },
        back() {
            this.$router.go(-1);
        }
    }
});