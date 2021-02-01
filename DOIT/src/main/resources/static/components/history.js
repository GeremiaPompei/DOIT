export default Vue.component('history', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%; flex-direction: column; align-items: center; justify-content: center;">
        <ul style="list-style-type: none;">
            <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="display: flex; align-items: center; justify-content: center;">back</button>
            <li v-for="(project, index) in projects" :key="index" style="padding-top: 10px">
                <div @click="go(index)">
                    <button class="btn btn-outline-info" style="width: 100%;">{{project.name}}</button>
                </div>
            </li>
        </ul>
    </div>
    `,
    data() {
        return {
            projects: [],
            role: this.$route.params.role
        }
    },
    async created() {
        var credential = JSON.parse(localStorage.getItem(key));
        this.projects = await (await fetch('/api/user/list-history?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole='+this.role)).json();
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