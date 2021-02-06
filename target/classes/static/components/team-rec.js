export default Vue.component('team-rec', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%; flex-direction: column; align-items: center; justify-content: center;">
        <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="display: flex; align-items: center; justify-content: center;">back</button>
        <div v-for="(project, index) in projects" :key="index">
            <div class="card border-primary mb-3" style="margin-top: 10px">
                <div class="card-header" style="text-align: center">
                    <button class="btn btn-outline-info" style="width: 100%;" @click="go(index)">{{project.name}}</button>
                </div>
                <div class="card-body">
                    <button type="button" class="btn btn-outline-primary" style="width: 100%;" @click="manageRec(project)">{{project.team.open?"close":"open"}}</button>
                </div>
            </div>
        </div>    
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
            await this.init();
            this.$emit('load',false);
            this.$emit('push', res);
        },
        go(i) {
            this.$router.push({path: '/project/'+this.projects[i].id});
        },
        back() {
            this.$router.go(-1);
        }
    }
});