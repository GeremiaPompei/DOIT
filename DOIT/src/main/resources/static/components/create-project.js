export default Vue.component('create-project', {
    template: `
    <div class='container'>
        <button @click="back()" type="button" class="btn btn-outline-primary">back</button>
        <form @submit.prevent="createProject()">
            <p>Name</p>
            <input placeholder="Project name..." type="text" v-model="name">
            <p>Description</p>
            <input placeholder="Project description..." type="text" v-model="description">
            <p>Category</p>
            <select @change="onChange($event)">
                <option key="-1" value="-1">---</option>
                <option v-for="(category, index) in categories" :value="index" :key="index">
                    {{category.name}}
                </option>
            </select>
            <input type="submit" value="Crea Progetto">
        </form>
    </div>
    `,
    data() {
        return {
            role: undefined,
            name: '',
            description: '',
            categories: [],
            categoryIndex: -1
        }
    },
    async created() {
        this.$emit('load',true);
        var credential = JSON.parse(localStorage.getItem(key));
        this.categories = await (await fetch('/api/user/list-categories?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole=project-proposer')).json();
        this.$emit('load',false);
    },
    methods: {
        async createProject() {
            if(this.categoryIndex<0) {
                this.$emit('push', "Seleziona una categoria!");
            } else {
                this.$emit('load',true);
                var credential = JSON.parse(localStorage.getItem(key));
                var res = await (await fetch('/api/project-proposer/create-project?iduser='+credential.id+'&tokenuser='+credential.token+
                '&name='+this.name+'&description='+this.description+'&idcategory='+this.categories[this.categoryIndex].name, {
                    method: 'POST'})).text();
                this.$emit('load',false);
                this.$emit('push', res);
                this.$router.go(-1);
            }
        },
        back() {
            this.$router.go(-1);
        },
        onChange(event) {
            this.categoryIndex = event.target.value;
        }
    }
});