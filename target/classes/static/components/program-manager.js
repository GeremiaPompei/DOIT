export default Vue.component('program-manager', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%; flex-direction: column; align-items: center; justify-content: center;">
        <role role="program-manager"></role>
        <hr class="my-4">
        <ul style="list-style-type: none;">
            <li v-for="(el, index) in list" :key="index" style="padding-top: 10px">
                <div @click="go(index)">
                    <button class="btn btn-outline-primary" style="width: 100%;">
                        {{el.name}}
                    </button>
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
                {name: 'List my partecipation rquest', path: {path: '/list-my-pr/program-manager'}},
            ]
        }
    },
    methods: {
        go(i) {
            this.$router.push(this.list[i].path);
        }
    }
});