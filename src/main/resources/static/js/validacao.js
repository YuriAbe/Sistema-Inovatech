document.addEventListener('DOMContentLoaded', function () {

    // ===== CPF: somente dígitos, max 11 =====
    document.querySelectorAll('input[data-mask="cpf"]').forEach(function (input) {
        input.addEventListener('input', function () {
            this.value = this.value.replace(/\D/g, '').slice(0, 11);
        });
    });

    // ===== Telefone: somente dígitos, max 11 =====
    document.querySelectorAll('input[data-mask="telefone"]').forEach(function (input) {
        input.addEventListener('input', function () {
            this.value = this.value.replace(/\D/g, '').slice(0, 11);
        });
    });

    // ===== Validação de CPF (algoritmo dígitos verificadores) =====
    function validarCpf(cpf) {
        cpf = cpf.replace(/\D/g, '');
        if (cpf.length !== 11) return false;
        if (/^(\d)\1{10}$/.test(cpf)) return false;

        var soma = 0;
        for (var i = 0; i < 9; i++) {
            soma += parseInt(cpf.charAt(i)) * (10 - i);
        }
        var dig1 = 11 - (soma % 11);
        if (dig1 >= 10) dig1 = 0;
        if (parseInt(cpf.charAt(9)) !== dig1) return false;

        soma = 0;
        for (var i = 0; i < 10; i++) {
            soma += parseInt(cpf.charAt(i)) * (11 - i);
        }
        var dig2 = 11 - (soma % 11);
        if (dig2 >= 10) dig2 = 0;
        return parseInt(cpf.charAt(10)) === dig2;
    }

    document.querySelectorAll('input[data-validate="cpf"]').forEach(function (input) {
        input.addEventListener('blur', function () {
            var feedback = this.parentElement.querySelector('.invalid-feedback');
            if (this.value && !validarCpf(this.value)) {
                this.classList.add('is-invalid');
                if (feedback) feedback.textContent = 'CPF inválido. Verifique os dígitos informados.';
            } else {
                this.classList.remove('is-invalid');
            }
        });
    });

    // ===== Preview de imagem do produto =====
    var imgInput = document.getElementById('imagemUrl');
    var imgPreview = document.getElementById('imagemPreview');
    if (imgInput && imgPreview) {
        imgInput.addEventListener('input', function () {
            if (this.value) {
                imgPreview.src = this.value;
                imgPreview.style.display = 'block';
            } else {
                imgPreview.style.display = 'none';
            }
        });
        imgPreview.addEventListener('error', function () {
            this.style.display = 'none';
        });
        if (imgInput.value) {
            imgPreview.src = imgInput.value;
            imgPreview.style.display = 'block';
        }
    }

    // ===== Confirmação de senha =====
    var senha = document.getElementById('senhaUsuario');
    var confirmar = document.getElementById('confirmarSenha');
    if (senha && confirmar) {
        confirmar.addEventListener('input', function () {
            var feedback = this.parentElement.querySelector('.invalid-feedback');
            if (this.value && this.value !== senha.value) {
                this.classList.add('is-invalid');
                if (feedback) feedback.textContent = 'As senhas não coincidem.';
            } else {
                this.classList.remove('is-invalid');
            }
        });
    }

    // ===== Auto-dismiss alerts after 5 seconds =====
    document.querySelectorAll('.alert-dismissible').forEach(function (alert) {
        setTimeout(function () {
            try {
                var bsAlert = bootstrap.Alert.getOrCreateInstance(alert);
                bsAlert.close();
            } catch(e) {}
        }, 5000);
    });
});
