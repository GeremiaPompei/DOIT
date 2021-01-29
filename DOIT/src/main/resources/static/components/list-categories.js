export default Vue.component('list-categories', {
    template: `
    <div class='container'>
        <ul>
            <button @click="back()" class="back"><i style="font-size:24px" class="fa">&#xf104;</i></button>
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
            categories: []
        }
    },
    async created() {
        var credential = JSON.parse(localStorage.getItem(key));
        this.categories = await (await fetch('/api/'+this.$route.params.role+'/list-categories?iduser='+credential.id+'&tokenuser='+credential.token)).json();
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