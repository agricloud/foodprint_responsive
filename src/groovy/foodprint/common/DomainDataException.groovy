package foodprint.common

class DomainDataException extends RuntimeException {

    private static final long serialVersionUID = 1

    def errors = [:]

    public DomainDataException(String message) {
        super(message)
    }

    public DomainDataException(String message, errors) {
        super(message)
        this.errors = errors
    }

    public def getErrors = {
        return this.errors
    }

    def getResult = {
        [success: false, message: this.getMessage(), errors: this.errors]
    }
}
