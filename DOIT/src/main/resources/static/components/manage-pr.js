export default Vue.component('accept-pr', {
    template: `
    <div class='container'>
        <button @click="back()" type="button" class="btn btn-outline-primary">back</button>
        <select @change="onChange($event)">
            <option key="-1" value="-1">---</option>
            <option v-for="(project, index) in projects" :value="index" :key="index">
                {{project.name}}
            </option>
        </select>
        <div v-for="(element, index) in users" :key="index">
            <button @click="show(element.id)">{{element.name}}</button>
            <button type="button" class="btn btn-outline-primary" @click="acceptPr(element.id)">accept</button>
            <input type="text" v-model="reason" placeholder="Reason...">
            <button type="button" class="btn btn-outline-secondary" @click="removePr(element.id)">remove</button>
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
            this.$emit('load',false);
            alert(res);
            this.init();
            this.users = [];
            this.prs = [];
        },
        async removePr(index) {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            var idPr = this.prs.find(pr => pr.pendingRole.idUser==index).id;
            var res = await (await fetch('/api/'+this.role+'/remove-pr?iduser='+credential.id+'&tokenuser='+credential.token+'&idpr='+idPr+'&reason='+this.reason, {method: 'DELETE'})).text();
            this.$emit('load',false);
            alert(res);
            this.init();
            this.users = [];
            this.prs = [];
        },
        back() {
            this.$router.go(-1);
        },
        show(id) {
            this.$router.push({path: '/user/'+id});
        },
        async onChange(event) {
            this.$emit('load',true);
            var project = this.projects[event.target.value];
            var credential = JSON.parse(localStorage.getItem(key));
            this.prs = await (await fetch('/api/'+this.role+'/list-other-pr?iduser='+credential.id+'&tokenuser='+credential.token+'&idproject='+project.id)).json();
            this.users = [];
            for(var i in this.prs) {
                this.users.push(await (await fetch('/api/search/user-by-id?id='+this.prs[i].pendingRole.idUser)).json());
            }
            this.$emit('load',false);
        }
    }
});