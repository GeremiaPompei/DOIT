export default Vue.component('manage-project-state', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%; flex-direction: column; align-items: center; justify-content: center;">
        <ul style="list-style-type: none;">
            <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="display: flex; align-items: center; justify-content: center;">back</button>
            <li v-for="(project, index) in projects" :key="index">
                <div class="card border-info mb-3" style="margin-top: 10px">
                    <div class="card-header" @click="go(index)" style="text-align: center">{{project.name+" : "+project.projectState.name}}</div>
                    <div class="card-body">
                        <div class="btn-group btn-group-toggle" data-toggle="buttons" style="display: flex">
                            <label class="btn btn-primary" :class="{'active': this.type=='users'}" style="flex: 1;">
                                <input type="radio" name="options" id="option1" autocomplete="off" @click="downgrade(project.id)"> <<<
                            </label>
                            <label class="btn btn-primary" :class="{'active': this.type=='projects'}" style="flex: 1;">
                                <input type="radio" name="options" id="option2" autocomplete="off" @click="upgrade(project.id)"> >>>
                            </label>
                        </div>
                    </div>
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
            this.projects = await (await fetch('/api/user/list-projects?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole=project-manager')).json();
            this.$emit('load', false);
        },
        async downgrade(id) {
            this.$emit('load', true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/project-manager/downgrade-state?iduser='+credential.id+'&tokenuser='+credential.token+'&idproject='+id, {method: "PUT"})).text();
            await this.init();
            this.$emit('load',false);
            this.$emit('push', res);
        },
        async upgrade(id) {
            this.$emit('load', true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/project-manager/upgrade-state?iduser='+credential.id+'&tokenuser='+credential.token+'&idproject='+id, {method: "PUT"})).text();
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