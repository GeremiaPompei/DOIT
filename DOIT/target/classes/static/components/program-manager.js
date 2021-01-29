export default Vue.component('program-manager', {
    template: `
    <div class='container'>
        <role role="program-manager"></role>
        <ul>
            <li v-for="(el, index) in list" :key="index">
                <div @click="go(index)">
                    <button>{{el.name}}</button>
                </div>
            </li>
        </ul>
    </div>
    `,
    data() {
        return {
            list: [
                {name: 'Send partecipation request to project proposer', path: {path: '/send-pr/program-manager'}},
                {name: 'Manage partecipation request designers', path: {path: '/manage-pr/program-manager'}},
                {name: 'Manage team registrations', path: {path: '/team-rec'}},
                {name: 'Set project manager', path: {path: '/set-project-manager'}},
            ]
        }
    },
    methods: {
        go(i) {
            this.$router.push(this.list[i].path);
        }
    }
});