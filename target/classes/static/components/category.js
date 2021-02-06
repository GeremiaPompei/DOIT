export default Vue.component('category', {
    template: 
    /*html*/`
        <div style="margin: 10px; padding: 10%; padding-top: 1%; flex-direction: column; align-items: center; justify-content: center;" >
            <div>
                <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="display: flex; align-items: center; justify-content: center;">back</button>
            </div>
            <div class="card border-info mb-3" style="margin-top: 10px">
              <div class="card-header" style="text-align: center">{{category.name}}</div>
              <div class="card-body">
                <p class="card-text">{{category.description}}</p>
              </div>
            </div>
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