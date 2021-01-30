export default Vue.component('list-categories', {
    template: `
    <div class='container'>
        <ul>
        <button @click="back()" type="button" class="btn btn-outline-primary">back</button>
            <li v-for="(category, index) in categories" :key="index">
                <div @click="go(index)">
                    <button>{{category.name}}</button>
                </div>
            </li>
        </ul>
    </div>
    `,
    data() {
        return {
            categories: [],
            role: this.$route.params.role
        }
    },
    async created() {
        var credential = JSON.parse(localStorage.getItem(key));
        this.categories = await (await fetch('/api/user/list-categories?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole='+this.role)).json();
    },
    methods: {
        go(i) {
            this.$router.push({path: '/category/'+this.categories[i].name});
        },
        back() {
            this.$router.go(-1);
        }
    }
});