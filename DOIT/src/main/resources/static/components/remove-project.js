export default Vue.component('remove-project', {
    template: `
    <div class='container'>
        <ul>
        <button @click="back()" type="button" class="btn btn-outline-primary">back</button>
            <li v-for="(project, index) in projects" :key="index">
                <button @click="go(index)">{{project.name}}</button>
                <button @click="remove(project.id)">remove</button>
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
    created() {
        this.init();
    },
    methods: {
        async init() {
            this.$emit('load', true);
            var credential = JSON.parse(localStorage.getItem(key));
            this.projects = await (await fetch('/api/user/list-projects?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole='+this.role)).json();
            this.$emit('load', false);
        },
        go(i) {
            this.$router.push({path: '/project/'+this.projects[i].id});
        },
        async remove(id) {
            this.$emit('load', true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/'+this.role+'/remove-project?iduser='+credential.id+'&tokenuser='+credential.token+'&idproject='+id, {method: "DELETE"})).text();
            await this.init();
            this.$emit('load',false);
            this.$emit('push', res);
        },
        back() {
            this.$router.go(-1);
        }
    }
});