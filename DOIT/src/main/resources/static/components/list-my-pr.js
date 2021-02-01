export default Vue.component('list-my-pr', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%">
        <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="padding-bottom: 10px; display: flex; align-items: center; justify-content: center;">back</button>
        <div v-for="(element, index) in prs" :key="index">
            <div class="card border-info mb-3" style="margin-top: 10px">
                <div class="card-header" style="text-align: center">
                    <button class="btn btn-outline-info" style="width: 100%; margin-bottom: 5px"@click="show(element.project.id)">{{element.project.name}}</button>
                </div>
                <div class="card-body">
                    <p class="card-text">{{element.description}}</p>
                </div>
                <div class="card-body">
                    <button class="btn btn-outline-danger" style="width: 100%" @click="remove(element.id)">remove</button>
                </div>
            </div>
        </div>    
    </div>
    `,
    data() {
        return {
            role: this.$route.params.role,
            prs: []
        }
    },
    created() {
        this.init();
    },
    methods: {
        async init() {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            this.prs = await (await fetch('/api/'+this.role+'/list-my-pr?iduser='+credential.id+'&tokenuser='+credential.token)).json();
            this.$emit('load',false);
        },
        async remove(id) {
            this.$emit('load',true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/'+this.role+'/remove-my-pr?iduser='+credential.id+'&tokenuser='+credential.token+'&idpr='+id, {method: "DELETE"})).text();
            await this.init();
            this.$emit('load',false);
            this.$emit('push', res);
        },
        back() {
            this.$router.go(-1);
        },
        show(id) {
            this.$router.push({path: '/project/'+id});
        },
    }
});