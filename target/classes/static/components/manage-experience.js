export default Vue.component('manage-experience', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%">
    <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="padding-bottom: 10px; display: flex; align-items: center; justify-content: center;">back</button>
    <form @submit.prevent="insert()">
        <div class="form-group">
            <input type="text" class="form-control" v-model="input" style="margin: 1rem 0;" placeholder="Experience...">
        </div>
        <div class="form-group">
            <input type="date" class="form-control" v-model="dateStart" placeholder="Start date">
        </div>
        <div class="form-group">
            <input type="date" class="form-control" v-model="dateStop" placeholder="Stop date">
        </div>
        <input type="submit" class="btn btn-outline-primary" style="width: 100%; margin: 5px 0;"  value="insert">
    </form>
    <div v-for="(experience, index) in experiences" :key="index">
        <div class="card border-info mb-3" style="margin-top: 10px">
            <div class="card-header" style="text-align: center">
                <h5>{{experience.dateStart+' - '+experience.dateStop}}</h5>
            </div>
            <div class="card-body">
                <p class="card-text">{{experience.experience}}</p>
            </div>
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