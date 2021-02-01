export default Vue.component('manage-category', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%; flex-direction: column; align-items: center; justify-content: center;">
        <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="padding-bottom: 10px; display: flex; align-items: center; justify-content: center;">back</button>
        <div class="card border-primary mb-3" style="margin-top: 10px">
            <div class="card-header">My categories</div>
            <div class="card-body">
                <ul style="list-style-type: none;">
                    <li v-for="(category, index) in myCategories" :key="index" style="padding-top: 20px">
                        <div @click="remove(index)">
                            <button class="btn btn-outline-info" style="width: 100%;">{{category.name}}</button>
                        </div>
                    </li>
                </ul>
            </div>
        </div>

        <div class="card border-primary mb-3">
            <div class="card-header">Other categories</div>
            <div class="card-body">
                <ul style="list-style-type: none;">
                    <li v-for="(category, index) in otherCategories" :key="index" style="padding-top: 10px">
                        <div @click="add(index)">
                            <button class="btn btn-outline-info" style="width: 100%;">{{category.name}}</button>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    `,
    data() {
        return {
            myCategories: [],
            otherCategories: [],
            role: this.$route.params.role
        }
    },
    created() {
        this.init();
    },
    methods: {
        async init() {
            this.otherCategories = [];
            var credential = JSON.parse(localStorage.getItem(key));
            this.myCategories = await (await fetch('/api/user/list-categories?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole='+this.role)).json();
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
            await this.init();
            this.$emit('load',false);
            this.$emit('push', res);
        },
        async remove(i) {
            this.$emit('load', true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/user/remove-category-to-role?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole='+this.$route.params.role+'&idcategory='+this.myCategories[i].name, {method: "DELETE"})).text();
            await this.init();
            this.$emit('load',false);
            this.$emit('push', res);
        },
        back() {
            this.$router.go(-1);
        }
    }
});