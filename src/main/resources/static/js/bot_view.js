window.onload = () => {
    async function fetchSteps() { //если у нас есть несколько скриптов с async, они могут выполняться в любом порядке.
        try {                    // То, что первое загрузится – запустится в первую очередь.
            const data = await fetch(`/bots/getSteps/${getBotId()}`); // await используется для ожидания результатов операций
            return await data.json();
        } catch(e) {
        console.log(e); //Метод console.log() выводит отладочную информацию в консоль, т.е. скрывая ее от пользователей.
        }
    }

    async function saveStep(formData) {
    const fd = new FormData(formData);
    fd.append("bot_id", getBotId());
    console.log(fd);
        try {
            const data = await fetch("/steps/save", {
                 method: "POST",
                 body: fd
            });

            return await data.json();
        } catch(e) {
            console.log(e)
        }
    }

    async function deleteStep(stepId) {
        try {
            const data = await fetch(`/steps/delete/${stepId}`, {
                 method: "DELETE",
            });

            return await data.json();
        } catch(e) {
            console.log(e)
        }
    }

    function createTable(steps){
            steps.forEach(({ id, question, answer }) => {

                   const stepRow = document.createElement("tr");
                   stepRow.setAttribute("test-id", `dialogListItem-${question, answer}`);


                // Создать строку удаления
                    const deleteStepButtonTd = document.createElement("td");
                    const deleteStepButtonImg = document.createElement("i");
                    deleteStepButtonImg.className = "fas fa-trash";
                    deleteStepButtonImg.setAttribute("data-id", id);
                    deleteStepButtonImg.setAttribute("test-id", 'dialog-delete-button');

                    deleteStepButtonTd.appendChild(deleteStepButtonImg);


                // Создать строку вопроса у шага
                   const openQStepDetailsTd = document.createElement("td");
                   const openQStepDetailsHref = document.createElement("a"); //Атрибут href указывает целевую ссылку, либо URL-адрес, либо фрагмент URL-адреса.
                   openQStepDetailsHref.innerText = `${question}`;
                   openQStepDetailsHref.setAttribute("test-id", 'question-name');

                   openQStepDetailsTd.appendChild(openQStepDetailsHref);


                   // Создать строку ответа у шага
                   const openAStepDetailsTd = document.createElement("td");
                   const openAStepDetailsHref = document.createElement("a"); //Атрибут href указывает целевую ссылку, либо URL-адрес, либо фрагмент URL-адреса.
                   openAStepDetailsHref.innerText = `${answer}`;
                   openAStepDetailsHref.setAttribute("test-id", 'bot-answer');

                   openAStepDetailsTd.appendChild(openAStepDetailsHref);

    // Добавить td в строки
                   stepRow.appendChild(deleteStepButtonTd); //appendChild позволяет вставить в конец какого-либо другой элемент
                   stepRow.appendChild(openQStepDetailsTd);
                   stepRow.appendChild(openAStepDetailsTd);
                   tableBody.appendChild(stepRow);
            })
        }

        async function createOrUpdateTable() {
            const steps = await fetchSteps();
            tableBody.innerHTML = "";
            createTable(steps);
        }

        const tableBody = document.getElementsByClassName("tableSteps_body")[0];
        const addStepForm = document.forms.addStep;

        addStepForm.addEventListener("submit", async (e) => { //добавить прослушиватель событий
            e.preventDefault(); //предотвратить дефолт
            await saveStep(addStepForm);
            await createOrUpdateTable();
            addStepForm.reset()
        })

        tableBody.addEventListener("click", async (e) => {
                const { target } = e;
                if(target.className === "fas fa-trash") {
                    const stepId = target.getAttribute("data-id");
                    await deleteStep(stepId);
                    await createOrUpdateTable();
                }
            })
            createOrUpdateTable();
        }
    function getBotId(){
        const botIdElement = document.getElementById('id');
        return botIdElement.getAttribute('value');
        }