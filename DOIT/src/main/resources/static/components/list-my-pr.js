export default Vue.component('list-my-pr', {
    template: `
    <div class='container'>
        <button @click="back()" type="button" class="btn btn-outline-primary">back</button>
        <div v-for="(element, index) in prs" :key="index">
            <button @click="show(element.project.id)">{{element.project.name}}</button>
            <p>{{element.description}}</p>
            <button @click="remove(element.id)">remove</button>
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
            alert(res);
        },
        back() {
            this.$router.go(-1);
        },
        show(id) {
            this.$router.push({path: '/project/'+id});
        },
    }
});