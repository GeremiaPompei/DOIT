export default Vue.component('evaluate', {
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
            <input type="text" v-model="evaluate" placeholder="Evaluation...">
            <button type="button" class="btn btn-outline-primary" @click="evaluateDesigner(element.id)">evaluate</button>
        </div>
        <button type="button" class="btn btn-outline-primary" @click="exitProject()">exit project</button>
    </div>
    `,
    data() {
        return {
            users: [],
            projects: [],
            indexProject: -1,
            evaluate: ''
        }
    },
    created() {
        this.init();
    },
    methods: {
        async init() {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            this.projects = await (await fetch('/api/user/list-projects?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole=project-manager')).json();
            this.$emit('load',false);
        },
        async evaluateDesigner(index) {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/project-manager/evaluate?iduser='+credential.id+'&tokenuser='+credential.token+'&iddesigner='+index+
                '&idproject='+this.projects[this.indexProject].id+'&evaluation='+this.evaluate, {method: 'PUT'})).text();
            this.$emit('load',false);
            alert(res);
            this.init();
        },
        async exitProject(index) {
            if(this.indexProject<0) {
                alert("Seleziona un progetto!");
            } else {
                this.$emit('load',true);
                var credential = JSON.parse(localStorage.getItem(key));
                var res = await (await fetch('/api/project-manager/exit-all?iduser='+credential.id+'&tokenuser='+credential.token+'&idproject='+this.projects[this.indexProject].id, {method: 'PUT'})).text();
                this.$emit('load',false);
                alert(res);
                this.init();
            }
            
        },
        back() {
            this.$router.go(-1);
        },
        show(id) {
            this.$router.push({path: '/user/'+id});
        },
        async onChange(event) {
            this.$emit('load',true);
            this.indexProject = event.target.value;
            var project = this.projects[this.indexProject];
            var credential = JSON.parse(localStorage.getItem(key));
            this.users = await (await fetch('/api/project-manager/list-designers?iduser='+credential.id+'&tokenuser='+credential.token+'&idproject='+project.id)).json();
            this.$emit('load',false);
        }
    }
});