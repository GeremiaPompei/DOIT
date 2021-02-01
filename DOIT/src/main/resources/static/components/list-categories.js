export default Vue.component('list-categories', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%; flex-direction: column; align-items: center; justify-content: center;">
        <ul style="list-style-type: none;">
            <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="display: flex; align-items: center; justify-content: center;">back</button>
            <li v-for="(category, index) in categories" :key="index" style="padding-top: 10px">
                <div @click="go(index)">
                    <button class="btn btn-outline-info" style="width: 100%;">{{category.name}}</button>
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