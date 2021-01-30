export default Vue.component('list-notifications', {
    template: `
    <div class='container'>
        <ul>
        <button @click="back()" type="button" class="btn btn-outline-primary">back</button>
            <li v-for="(notification, index) in notifications" :key="index">
                <div>
                    <button>{{notification}}</button>
                </div>
            </li>
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
        var credential = JSON.parse(localStorage.getItem(key));
        this.notifications = await (await fetch('/api/user/list-notifications?iduser='+credential.id+'&tokenuser='+credential.token+'&idrole='+this.role)).json();
    },
    methods: {
        back() {
            this.$router.go(-1);
        }
    }
});