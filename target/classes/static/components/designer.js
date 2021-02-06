export default Vue.component('designer', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%; flex-direction: column; align-items: center; justify-content: center;">
        <role role="designer"></role>
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
                {name: 'Send partecipation request to program manager', path: {path: '/send-pr/designer'}},
                {name: 'Manage pregress experience designer', path: {path: '/manage-experience'}},
                {name: 'Remove project', path: {path: '/remove-project/designer'}},
                {name: 'List my partecipation rquest', path: {path: '/list-my-pr/designer'}},
            ]
        }
    },
    methods: {
        go(i) {
            this.$router.push(this.list[i].path);
        }
    }
});