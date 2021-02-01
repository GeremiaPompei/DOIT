export default Vue.component('remove-project', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%">
        <ul style="list-style-type: none;">
            <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="padding-bottom: 10px; display: flex; align-items: center; justify-content: center;">back</button>
            <li v-for="(project, index) in projects" :key="index" style="margin-top: 30px">
                <button class="btn btn-outline-info" style="width: 100%; margin-bottom: 5px" @click="go(index)">{{project.name}}</button>
                <button class="btn btn-outline-danger" style="width: 100%;" @click="remove(project.id)">remove</button>
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