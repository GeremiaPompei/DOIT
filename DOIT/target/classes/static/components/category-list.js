export default Vue.component('category-list', {
    template: `
    <div class='container'>
        <button v-for="(category, index) in categories" key="index" @onclick="select(index)">
            {{category.name}}
        </button>
    </div>
    `,
    data() {
        return {
            categories: []
        }
    },
    created() {
        fetch('/api/search/categories')
        .then(res => res.json())
        .then(res => this.categories = res);
    },
    methods: {
        select(index) {
            this.$emit('load',true);
                fetch('/api/user/add-role', {
                    method: 'POST', 
                    body: localStorage.getItem(key)+" "+ this.$route.params.role +" "+this.categories[index].name,
                    headers: {'Content-Type': 'application/json'}})
                .then(res => res.text())
                .then(res => {
                    this.$emit('load',false);
                    this.$router.go(-1);
                });
        }
    }
});