export default Vue.component('accept-pr', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%">
        <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="padding-bottom: 10px; display: flex; align-items: center; justify-content: center;">back</button>
        <div class="form-group" style="margin-top: 10px">
            <select @change="onChange($event)" class="form-control">
                <option key="-1" value="-1">---</option>
                <option v-for="(project, index) in projects" :value="index" :key="index">
                    {{project.name}}
                </option>
            </select>
        </div>
        <div v-for="(element, index) in users" :key="index">
            <div class="card border-primary mb-3" style="margin-top: 10px">
                <div class="card-header" style="text-align: center">
                    <button class="btn btn-outline-info" style="width: 100%; margin-bottom: 5px"@click="show(element.id)">{{element.name}}</button>
                </div>
                <div class="card-body">
                    <button type="button" class="btn btn-outline-primary" style="width: 100%; margin-bottom: 10px" @click="acceptPr(element.id)">accept</button>
                </div>
                <div class="card-body">
                    <input class="form-control" type="text" v-model="reason" placeholder="Insert the reason why this program manager is not going to be selected">
                    <button type="button" class="btn btn-outline-danger" style="width: 100%; margin-bottom: 10px" @click="removePr(element.id)">remove</button>
                </div>
            </div>
        </div>    
    </div>
    `,
    data() {
        return {
            users: [],
            projects: [],
            prs: [],
            role: this.$route.params.role,
            reason: ''
        }
    },
    created() {
        this.init();
    },
    methods: {
        async init() {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            this.projects = await (await fetch('/api/user/list-projects?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole='+this.role)).json();
            this.$emit('load',false);
        },
        async acceptPr(index) {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            var idPr = this.prs.find(pr => pr.pendingRole.idUser==index).id;
            var res = await (await fetch('/api/'+this.role+'/accept-pr?iduser='+credential.id+'&tokenuser='+credential.token+'&idpr='+idPr, {method: 'PUT'})).text();
            this.users = [];
            this.prs = [];
            await this.init();
            this.$emit('load',false);
            this.$emit('push', res);
        },
        async removePr(index) {
            if(this.reason=='')
                this.$emit('push', 'Filds missed!');
            else {
                this.$emit('load',true);
                var credential = JSON.parse(localStorage.getItem(key));
                var idPr = this.prs.find(pr => pr.pendingRole.idUser==index).id;
                var res = await (await fetch('/api/'+this.role+'/remove-pr?iduser='+credential.id+'&tokenuser='+credential.token+'&idpr='+idPr+'&reason='+this.reason, {method: 'DELETE'})).text();
                this.users = [];
                this.prs = [];
                await this.init();
                this.$emit('load',false);
                this.$emit('push', res);
            }
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
                var project = this.projects[index];
                var credential = JSON.parse(localStorage.getItem(key));
                this.prs = await (await fetch('/api/'+this.role+'/list-other-pr?iduser='+credential.id+'&tokenuser='+credential.token+'&idproject='+project.id)).json();
                this.users = [];
                for(var i in this.prs) {
                    this.users.push(await (await fetch('/api/search/user-by-id?id='+this.prs[i].pendingRole.idUser)).json());
                }
                this.$emit('load',false);
            }
        }
    }
});