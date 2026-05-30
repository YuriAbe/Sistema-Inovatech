let itens = [];

function adicionarItem() {
    let produtoInput = document.getElementById("produto").value;
    let quantidade = document.getElementById("quantidade").value;

    if (!produtoInput || quantidade <= 0) {
        alert("Preencha corretamente o produto e a quantidade!");
        return;
    }

    let partes = produtoInput.split(" - ");
    let produtoId = partes[0];
    let produtoNome = partes[1];

    let options = document.querySelectorAll("#listaProdutos option");
    let preco = null;

    options.forEach(opt => {
        if (opt.value === produtoInput) {
            preco = opt.getAttribute("data-preco");
        }
    });

    if (!preco) {
        alert("Produto inválido! Selecione um produto da lista.");
        return;
    }

    let subtotal = quantidade * preco;

    itens.push({
        produto: { idProduto: produtoId },
        nomeProduto: produtoNome,
        quantidade: parseInt(quantidade),
        preco: parseFloat(preco),
        subtotal: subtotal
    });

    document.getElementById("produto").value = "";
    document.getElementById("quantidade").value = "";

    renderTabela();
}

function removerItem(index) {
    itens.splice(index, 1);
    renderTabela();
}

// NOVO: função para alterar quantidade diretamente na tabela
function alterarQuantidade(index, novaQuantidade) {

    if (novaQuantidade <= 0) {
        alert("Quantidade inválida!");
        return;
    }

    let item = itens[index];

    item.quantidade = parseInt(novaQuantidade); // atualiza quantidade
    item.subtotal = item.quantidade * item.preco; // recalcula subtotal

    renderTabela(); // atualiza tela
}

function renderTabela() {
    let tabela = document.getElementById("tabela-itens");
    let emptyRow = document.getElementById("empty-row");

    tabela.innerHTML = "";

    if (itens.length === 0) {
        tabela.innerHTML = `<tr class="empty-row" id="empty-row"><td colspan="5">Nenhum produto adicionado ao pedido.</td></tr>`;
        document.getElementById("totalPedido").innerText = "0,00";
        return;
    }

    let total = 0;

    itens.forEach((item, index) => {
        total += item.subtotal;

        tabela.innerHTML += `
                <tr>
                    <td>${item.nomeProduto}</td>

                    <!-- ANTIGO: quantidade apenas como texto
                    <td>${item.quantidade}</td>
                    -->

                    <!-- NOVO: campo editável para alterar quantidade -->
                    <td>
                        <input type="number" min="1" value="${item.quantidade}" 
                               onchange="alterarQuantidade(${index}, this.value)" 
                               style="width:60px;">
                    </td>

                    <td>R$ ${item.preco.toFixed(2).replace('.', ',')}</td>
                    <td>R$ ${item.subtotal.toFixed(2).replace('.', ',')}</td>
                    <td>
                        <button class="btn-remove" onclick="removerItem(${index})">
                            Remover
                        </button>
                    </td>
                </tr>
            `;
    });

    document.getElementById("totalPedido").innerText = total.toFixed(2).replace('.', ',');
}

function salvarPedido() {

    const alunoInput = document.getElementById("aluno").value;

    if (!alunoInput) {
        alert("Selecione um aluno antes de salvar!");
        return;
    }

    if (!alunoInput.includes(" - ")) {
        alert("Selecione um aluno válido da lista!");
        return;
    }

    if (itens.length === 0) {
        alert("Adicione ao menos um produto ao pedido!");
        return;
    }

    // Desestruturação moderna
    const [alunoId, alunoNome] = alunoInput.split(" - ");

    const pedido = {

        // IMPORTANTE:
        // o nome deve ser IGUAL ao da entidade Java
        aluno: {
            idAluno: parseInt(alunoId)
        },

        itens: itens
    };

    fetch("/pedidos", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(pedido)
    })
        .then(async res => {

            if (!res.ok) {
                throw new Error("Erro ao salvar pedido");
            }

            return res.json();
        })
        .then(data => {
            alert("Pedido salvo com sucesso!");
            location.reload();
        })
        .catch(err => {
            console.error(err);
            alert("Erro ao salvar o pedido.");
        });
}