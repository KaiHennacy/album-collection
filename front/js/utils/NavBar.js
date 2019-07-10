class NavBar {

    constructor(){
    }

    createElement(elementType) {
        if(!elementType) {
            throw new Error('Must pass valid HTML Element')
        }

        const createdElement = document.createElement(elementType)
        return createdElement
    }

    
    createNavBar() {
        const navElement = this.createElement('nav')
        const ulElement = this.createElement('ul')
        const menuButton = this.createElement('button')
        let liElements

        const articles = ['home', 'other']

        navElement.classList.add('nav')


        liElements = articles.map((article) => {
            const liElement = document.createElement('li')
            const sectionBtn = document.createElement('button')

            sectionBtn.textContent = article.charAt(0).toUpperCase() + article.slice(1)

            sectionBtn.addEventListener('click', () => {
                this.toggleHidden(article)
            })

            liElement.classList.add('nav_list-item')

            liElement.appendChild(sectionBtn)

            return liElement;
        })

        ulElement.classList.add('nav_list')
        ulElement.classList.toggle('hidden')

        liElements.forEach((element) => {
            ulElement.appendChild(element);
        })

        menuButton.classList.add('nav_button')
        menuButton.textContent = 'MENU'

        menuButton.addEventListener('click', () => {
            menuButton.textContent = 'MENU'
            ulElement.classList.toggle('hidden')
            if(!ulElement.classList.contains('hidden')){
                menuButton.textContent = 'MENU ^'
            }
        })


        
        window.addEventListener('click', (event) => {
            console.log(event)
            if(!event.target.matches('button')&&!ulElement.classList.contains('hidden')){
                ulElement.classList.toggle('hidden')
                menuButton.textContent = 'MENU'
            }
        })

        navElement.appendChild(menuButton)
        navElement.appendChild(ulElement)

        return navElement
    }

    toggleHidden(article){
        let articles = document.getElementsByTagName('ARTICLE')
        for( var i = 0; i < articles.length; i++){
            if((!articles.item(i).classList.contains('hidden'))){
                articles.item(i).classList.toggle('hidden')
            }
            if(articles.item(i).classList.contains(`${article}`)){
                articles.item(i).classList.toggle('hidden')
            }
        }
        return articles
    }
}

module.exports = NavBar