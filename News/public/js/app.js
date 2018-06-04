(() => {
    const PARENT_URL='localhost:8080';
    const INDEX_URL='index';
    const ADD_URL = 'add';
    const SHOW_URL = 'show';
    const STORIES_URL = "stories";
    let container = document.getElementById('container');

    let sendXmlHttpRequest = (url) => {
        return new Promise((resolve, reject) => {
            let xmlHttp = new XMLHttpRequest();
            xmlHttp.open('GET', url);

            xmlHttp.onload = () => resolve(xmlHttp.response.trim(), xmlHttp.status);
            xmlHttp.onerror = () => reject(xmlHttp.response.trim());

            xmlHttp.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
            xmlHttp.send();
        });
    };

    let setupIndex = () => {
        let request = sendXmlHttpRequest(STORIES_URL);
        request.then((response) => {
            response = JSON.parse(response);
            if(response.links.length == 0){
                let h3 = document.createElement('h3');
                h3.textContent = 'No posts so far';
                container.appendChild(h3);
            }
            else{
                for(let i in response.links){
                    let div = document.createElement('div');
                    let a  = document.createElement('a');
                    a.href = "http://localhost:8080/show.html?q=" + response.links[i]['_id'];
                    a.textContent = response.links[i]['title'];
                    div.appendChild(a);
                    container.appendChild(div);
                }
            }
        }).catch((reason) => {
            console.log(`${reason}`);
        });
    };
    let setupShow = () => {
        let request =  sendXmlHttpRequest(SHOW_URL+
            '/'+(new URL(window.location.href)).searchParams.get('q'));
        request.then((response, status) => {
            response = JSON.parse(response);
            console.log(response);
            if(!response.error){
                //stylesheet
                document.head.innerHTML += response.blog.stylesheets;
                let element = document.createElement('h3');
                element.innerHTML = response.blog.title;
                element.class="header";
                container.appendChild(element);

                element = document.createElement('span');
                element.innerHTML = 'By: ' + response.blog.from;
                container.appendChild(element);

                element = document.createElement('div');
                element.classList="block";
                element.innerHTML = response.blog.article;
                container.appendChild(element);

                //scripts
                document.body.innerHTML += response.blog.scripts;
            }else{
                let element = document.createElement('h4');
                element.textContent = `${response.error}`
                container.appendChild(element);
            }
        });
    };

    let setupAdd = () => {
    };

    document.body.onload = () => {
        switch(page){
            case 'index':
                setupIndex();
                break;
            case 'add':
                setupAdd();
                break;
            case 'show':
                setupShow();
                break;
        }
    };
})();
