export default Vue.component('manage-category', {
    template: `
    <div class='container'>
    <button @click="back()" type="button" class="btn btn-outline-primary">back</button>
        <p>My category</p>
        <ul>
            <li v-for="(category, index) in myCategories" :key="index">
                <div @click="remove(index)">
                    <button>{{category.name}}</button>
                </div>
            </li>
        </ul>
        <p>Other category</p>
        <ul>
            <li v-for="(category, index) in otherCategories" :key="index">
                <div @click="add(index)">
                    <button>{{category.name}}</button>
                </div>
            </li>
        </ul>
    </div>
    `,
    data() {
        return {
            myCategories: [],
            otherCategories: []
        }
    },
    created() {
        this.init();
    },
    methods: {
        async init() {
            this.otherCategories = [];
            var credential = JSON.parse(localStorage.getItem(key));
            this.myCategories = await (await fetch('/api/'+this.$route.params.role+'/list-categories?iduser='+credential.id+'&tokenuser='+credential.token)).json();
            var allCategories = await (await fetch('/api/search/categories')).json();
            allCategories.forEach(c => {
                if(!this.myCategories.map(cat => cat.name).includes(c.name))
                    this.otherCategories.push(c);
            });
        },
        async add(i) {
            this.$emit('load', true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/user/add-category-to-role?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole='+this.$route.params.role+'&idcategory='+this.otherCategories[i].name, {method: "PUT"})).text();
            this.init();
            this.$emit('load', false);
            alert(res);
        },
        async remove(i) {
            this.$emit('load', true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/user/remove-category-to-role?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole='+this.$route.params.role+'&idcategory='+this.myCategories[i].name, {method: "DELETE"})).text();
            this.init();
            this.$emit('load', false);
            alert(res);
        },
        back() {
            this.$router.go(-1);
        }
    }
});