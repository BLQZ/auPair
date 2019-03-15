import mongoose, { Schema } from 'mongoose'

const comentarioSchema = new Schema({
    userId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User'
    },
    anuncioId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Anuncio'
    },
    contenido: {
        type: String,
        required: true
    }
}, {
    timestamps: true,
    toJSON: {
        virtuals: true,
        transform: (obj, ret) => { delete ret._id }
    }
})

comentarioSchema.methods = {
    view(full) {
        const view = {
            // simple view
            id: this.id,
            userId: this.userId,
            anuncioId: this.anuncioId,
            contenido: this.contenido,
            createdAt: this.createdAt,
            updatedAt: this.updatedAt
        }

        return full ? {
            ...view
            // add properties for a full view
        } : view
    }
}

const model = mongoose.model('Comentario', comentarioSchema)

export const schema = model.schema
export default model