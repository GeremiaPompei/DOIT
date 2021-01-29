export default Vue.component('category', {
    template: `
        <div class='container'>
            <button @click="back()" class="back"><i style="font-size:24px" class="fa">&#xf104;</i></button>
            <p>Name</p>
            <h3 class="el">{{category.name}}</h3>
            <p>Description</p>
            <h3 class="el">{{category.description}}</h3>
        </div>
        `,
        data() {
            return {
                category: {}
            }
        },
        async created() {
            this.$emit('load',true);
            this.category = await (await fetch('/api/search/category-by-id?id='+this.$route.params.id)).json();
            this.$emit('load',false);
        },
        methods: {
            back() {
                this.$router.go(-1);
            }
        }
});