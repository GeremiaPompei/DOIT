export default Vue.component('manage-experience', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%">
    <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="padding-bottom: 10px; display: flex; align-items: center; justify-content: center;">back</button>
    <form @submit.prevent="insert()">
        <input type="text" v-model="input" placeholder="Esperienza...">
        <input type="text" v-model="dateStart" placeholder="Data start...">
        <input type="text" v-model="dateStop" placeholder="Data stop...">
        <input type="submit" value="insert">
    </form>
    <div v-for="(experience, index) in experiences" :key="index">
        <div class="card border-info mb-3" style="margin-top: 10px">
            <div class="card-header" style="text-align: center">
                <h3>{{experience.dateStart+' - '+experience.dateStop}}</h3>
            </div>
            <div class="card-body">
                <p class="card-text">{{experience.experience}}</p>
            </div>
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
            if(this.input=='')
                this.$emit('push', 'Filds missed!');
            else {
                this.$emit('load', true);
                var credential = JSON.parse(localStorage.getItem(key));
                var res = await (await fetch('/api/designer/insert-pregress-experience?iduser='+credential.id+'&tokenuser='+credential.token+'&experience='+this.input
                    +'&datestart='+this.dateStart+'&datefinish='+this.dateStop, {method: "POST"})).text();
                await this.init();
                this.$emit('load',false);
                this.$emit('push', res);
            }
        },
        back() {
            this.$router.go(-1);
        }
    }
});