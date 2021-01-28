export default Vue.component('create-project', {
    template: `
    <div class='container'>
        <button @click="back()" class="back"><i style="font-size:24px" class="fa">&#xf104;</i></button>
        <form @submit.prevent="createProject()">
            <p>Name</p>
            <input placeholder="Project name..." type="text" v-model="name">
            <p>Description</p>
            <input placeholder="Project description..." type="text" v-model="description">
            <p>Category</p>
            <select @change="onChange($event)">
                <option key="-1">---</option>
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
        this.categories = await (await fetch('/api/project-proposer/list-categories?iduser='+credential.id+'&tokenuser='+credential.token)).json();
        this.$emit('load',false);
    },
    methods: {
        async createProject() {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/project-proposer/create-project?iduser='+credential.id+'&tokenuser='+credential.token+
            '&name='+this.name+'&description='+this.description+'&idcategory='+this.categories[this.categoryIndex].name, {
            method: 'POST'})).text();
            if(res!="success")
                alert(res);
            this.$emit('load',false);
        },
        back() {
            this.$router.go(-1);
        },
        onChange(event) {
            this.categoryIndex = event.target.value;
        }
    }
});