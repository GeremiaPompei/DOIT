export default Vue.component('cerca', {
    template: `
        <div class='container'>
            <input placeholder="Inserisci parola..." type="text" v-model="input" class="el">
            <h3 class="el">Hai inserito: {{input}}</h3>
        </div>
        `,
    data() {
        return {
            input: ''
        }
    }
});