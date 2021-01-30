export default Vue.component('manage-experience', {
    template: `
    <div class='container'>
        <button @click="back()" type="button" class="btn btn-outline-primary">back</button>
        <form @submit.prevent="">
        <input type="text" v-model="input" placeholder="Esperienza...">
        <input type="text" v-model="dateStart" placeholder="Data start...">
        <input type="text" v-model="dateStop" placeholder="Data stop...">
        <input @click="insert()" type="submit" value="insert">
        </form>
        <div v-for="(experience, index) in experiences" :key="index">
            <p>data e ora</p>
            <h3>{{experience.dateStart+' - '+experience.dateStop}}</h3>
            <p>esperienza</p>
            <h3>{{experience.experience}}</h3>
        </div>
    </div>
    `,
    data() {
        return {
            input: '',
            dateStart: '',
            dateStop: '',
            experiences: []
        }
    },
    created() {
        this.init();
    },
    methods: {
        async init() {
            this.$emit('load', true);
            var credential = JSON.parse(localStorage.getItem(key));
            this.experiences = await (await fetch('/api/designer/visualize-pregress-experiences?iduser='+credential.id+'&tokenuser='+credential.token)).json();
            this.$emit('load', false);
        },
        async insert() {
            this.$emit('load', true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/designer/insert-pregress-experience?iduser='+credential.id+'&tokenuser='+credential.token+'&experience='+this.input
                +'&datestart='+this.dateStart+'&datefinish='+this.dateStop, {method: "POST"})).text();
            this.$emit('load', false);
            alert(res);
            this.init();
        },
        back() {
            this.$router.go(-1);
        }
    }
});