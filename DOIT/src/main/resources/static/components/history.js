export default Vue.component('history', {
    template: `
    <div class='container'>
        <ul>
            <button @click="back()" class="back"><i style="font-size:24px" class="fa">&#xf104;</i></button>
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
        this.projects = await (await fetch('/api/'+this.$route.params.role+'/list-history?iduser='+credential.id+'&tokenuser='+credential.token)).json();
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