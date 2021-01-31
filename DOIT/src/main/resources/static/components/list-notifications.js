export default Vue.component('list-notifications', {
    template: `
    <div class='container'>
        <ul>
        <button @click="back()" type="button" class="btn btn-outline-primary">back</button>
            <div v-for="(notification, index) in notifications" :key="index">
                <p>{{notification.localDateTime}}</p>
                <h3>{{notification.notification}}</h3>
            </div>
        </ul>
    </div>
    `,
    data() {
        return {
            notifications: [],
            role: this.$route.params.role
        }
    },
    async created() {
        this.$emit('load', true);
        var credential = JSON.parse(localStorage.getItem(key));
        this.notifications = await (await fetch('/api/user/list-notifications?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole='+this.role)).json();
        var res = await (await fetch('/api/user/remove-notifications?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole='+this.role, {method: "DELETE"})).text();
        this.$emit('load',false);
        this.$emit('push', res);
    },
    methods: {
        back() {
            this.$router.go(-1);
        }
    }
});