window.onload = () => {
    async function fetchBots() { //если у нас есть несколько скриптов с async, они могут выполняться в любом порядке.
        try {                    // То, что первое загрузится – запустится в первую очередь.
            const data = await fetch("/bots/getAll"); // await используется для ожидания результатов операций
            return await data.json();
        } catch(e) {
        console.log(e); //Метод console.log() выводит отладочную информацию в консоль, т.е. скрывая ее от пользователей.
        }
    }

    async function saveBot(formData) {
        try {
            const data = await fetch("/bots/save", {
                 method: "POST",
                 body: new FormData(formData)
            });

            return await data.json();
        } catch(e) {
            console.log(e)
        }
    }

    async function deleteBot(botId) {
        try {
            const data = await fetch(`/bots/delete/${botId}`, {
                 method: "DELETE",
            });
            return await data.json();
        } catch(e) {
            console.log(e)
        }
    }

        async function startBot(botId) {
            try {
                const data = await fetch(`/bots/start/${botId}`, {
                     method: "POST",
                });
                return await data.json();
            } catch(e) {
                console.log(e)
            }
        }

        async function stopBot() {
                    try {
                        const data = await fetch(`/bots/stop/`, {
                             method: "POST",
                        });
                        return await data.json();
                    } catch(e) {
                        console.log(e)
                    }
                }

    function createTable(bots){
        bots.forEach(({ id, name }) => {

               const botRow = document.createElement("tr");


            // Создать строку удаления
                const deleteBotButtonTd = document.createElement("td");
                const deleteBotButtonImg = document.createElement("i");
                deleteBotButtonImg.className = "fas fa-trash";
                deleteBotButtonImg.setAttribute("data-id", id);

                deleteBotButtonTd.appendChild(deleteBotButtonImg);


            // Создать строку имени бота
               const openBotDetailsTd = document.createElement("td");
               const openBotDetailsHref = document.createElement("a"); //Атрибут href указывает целевую ссылку, либо URL-адрес, либо фрагмент URL-адреса.
               openBotDetailsHref.innerText = `${name}`;
               openBotDetailsHref.href = `bots/get/${id}`;

               openBotDetailsTd.appendChild(openBotDetailsHref);

               //Создать строку активации бота
               const playBotButtonTd = document.createElement("td");
               const playBotButtonHref = document.createElement("a");
               playBotButtonHref.className = "fas fa-play";
               playBotButtonHref.setAttribute("data-id", id);

               playBotButtonTd.appendChild(playBotButtonHref);

               //Создать строку остановки бота
               const stopBotButtonTd = document.createElement("td");
               const stopBotButtonHref = document.createElement("a");
               stopBotButtonHref.className = "fas fa-square";
               //stopBotButtonHref.setAttribute("data-id", id);

               stopBotButtonTd.appendChild(stopBotButtonHref);

            // Добавить td в строки
               botRow.appendChild(deleteBotButtonTd); //appendChild позволяет вставить в конец какого-либо другой элемент
               botRow.appendChild(openBotDetailsTd);
               botRow.appendChild(playBotButtonTd);
               botRow.appendChild(stopBotButtonTd);
               tableBody.appendChild(botRow);
        })
    }

    async function createOrUpdateTable() {
        const bots = await fetchBots();
        tableBody.innerHTML = "";
        createTable(bots);
    }

    const tableBody = document.getElementsByClassName("tableBots_body")[0];
    const addBotForm = document.forms.addBot;

    addBotForm.addEventListener("submit", async (e) => { //добавить прослушиватель событий
        e.preventDefault(); //предотвратить дефолт
        await saveBot(addBotForm);
        await createOrUpdateTable();
        addBotForm.reset()
    })

    tableBody.addEventListener("click", async (e) => {
        const { target } = e;
        if(target.className === "fas fa-trash") {
            const botId = target.getAttribute("data-id");
            await deleteBot(botId);
            await createOrUpdateTable();
        } else
                if(target.className === "fas fa-play") {
                    const botId = target.getAttribute("data-id");
                    await startBot(botId);
                } else
                        if(target.className === "fas fa-square") {
                        await stopBot();
                        }
    })

    createOrUpdateTable();


}