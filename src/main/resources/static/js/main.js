'use strict'


function ajax(){
    let data_save = "";

    $.ajax({
        url: "/main",
        type: "POST",
        async:false,
        success : function(data){
            data_save = data;
        },
        error : function(){
            alert("에러")
        }
    });

    return data_save;
}

function loadData() {
    let str = ajax();

    // return fetch("data/data.json")
    //    .then((response) => response.json())
    //     .then(json => json.items);
    return JSON.parse(str)["items"];

}

function addHTML(items){
    const container = document.querySelector('.items-ul');
    container.innerHTML = items.map(item => createHTMLString(item)).join('');
}

function createHTMLString(item) {
    return `
    <li class="item">
        <img src="${item.image}" alt="${item.type}" class="item_thumbnail">
        <span class="item_description">${item.gender}, ${item.size}</span>
    </li>
    `;
}

function loadFilteredData(event, items) {
    const key = event.target.dataset.key;
    const value = event.target.dataset.value;

    if(key==null||value==null) {
        return;
    }

    const filtered = items.filter(item => item[key] == value);

    addHTML(filtered);
}

function setEventListeners(items) {
    const logo = document.querySelector('.logo');
    const buttons = document.querySelector('.buttons');

    logo.addEventListener('click', ()=>addHTML(items));
    buttons.addEventListener('click', event => loadFilteredData(event, items));
}

// loadData().then(items => {
//     addHTML(items);
//     setEventListeners(items);
// });

let items = loadData();

window.onload = function () {
    addHTML(items);
    setEventListeners(items);
}




