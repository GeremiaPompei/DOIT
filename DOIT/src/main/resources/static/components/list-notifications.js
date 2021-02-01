export default Vue.component('list-notifications', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%">
        <button @click="back()" type="button" class="bbtn btn-primary btn-lg btn-block" style="padding-bottom: 10px; display: flex; align-items: center; justify-content: center;">back</button>
        <div v-for="(notification, index) in notifications" :key="index">
            <div class="card border-info mb-3" style="margin-top: 10px">
                <div class="card-header" style="text-align: center">
                    <h3>{{notification.localDateTime}}</h3>
                </div>
                <div class="card-body">
                    <p class="card-text">{{notification.notification}}</p>
                </div>
            </div>
        </div>  
        <div v-show='notifications.length' class="card-body">
            <button type="button" class="btn btn-outline-danger" style="width: 100%; margin-bottom: 10px" @click="removeAll()">remove</button>
        </div>  
    </div>
    `,
    data() {
        return {
            notifications: [],
            role: this.$route.params.role
        }
    },
    created() {
        this.init();
    },
    methods: {
        async init() {
            this.$emit('load', true);
            var credential = JSON.parse(localStorage.getItem(key));
            this.notifications = await (await fetch('/api/user/list-notifications?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole='+this.role)).json();
            this.$emit('load',false);
        },
        async removeAll() {
            this.$emit('load', true);
            var credential = JSON.parse(localStorage.getItem(key));
            var res = await (await fetch('/api/user/remove-notifications?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole='+this.role, {method: "DELETE"})).text();
            await this.init();
            this.$emit('load',false);
            this.$emit('push',res);
        },
        back() {
            this.$router.go(-1);
        }
    }
});