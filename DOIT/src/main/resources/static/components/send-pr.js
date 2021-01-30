export default Vue.component('send-pr', {
    template: `
    <div class='container'>
        <button @click="back()" type="button" class="btn btn-outline-primary">back</button>
        <select @change="onChange($event)">
            <option key="-1" value="-1">---</option>
            <option v-for="(category, index) in categories" :value="index" :key="index">
                {{category.name}}
            </option>
        </select>
        <ul>
            <li v-for="(element, index) in projects" :key="index">
                <button @click="show(element.id)">{{element.name}}</button>
                <button type="button" class="btn btn-outline-primary" @click="sendPr(element.id)">send</button>
            </li>
        </ul>
    </div>
    `,
    data() {
        return {
            categories: [],
            projects: [],
            role: this.$route.params.role
        }
    },
    created() {
        this.init();
    },
    methods: {
        async init() {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            this.categories = await (await fetch('/api/user/list-categories?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole='+this.role)).json();
            this.$emit('load',false);
        },
        async sendPr(index) {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/'+this.role+'/send-pr?iduser='+credential.id+'&tokenuser='+credential.token+'&idproject='+index, {method: 'POST'})).text();
            this.$emit('load',false);
            alert(res);
            this.init();
        },
        back() {
            this.$router.go(-1);
        },
        show(id) {
            this.$router.push({path: '/project/'+id});
        },
        async onChange(event) {
            this.$emit('load',true);
            var category = this.categories[event.target.value];
            var credential = JSON.parse(localStorage.getItem(key));
            this.projects = await (await fetch('/api/'+this.role+'/list-projects-by-category?iduser='+credential.id+'&tokenuser='+credential.token+'&idcategory='+category.name)).json();
            this.$emit('load',false);
        }
    }
});