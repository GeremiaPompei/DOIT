export default Vue.component('project-manager', {
    template: 
    /*html*/`
    <div class='' style="margin: 10px; padding: 10%; padding-top: 1%; flex-direction: column; align-items: center; justify-content: center;">
        <role role="project-manager"></role>
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
                {name: 'Manage project state', path: {path: '/manage-project-state'}},
                {name: 'Evaluate designers', path: {path: '/evaluate'}},
                {name: 'Remove project', path: {path: '/remove-project-pjm'}},
            ]
        }
    },
    methods: {
        go(i) {
            this.$router.push(this.list[i].path);
        }
    }
});