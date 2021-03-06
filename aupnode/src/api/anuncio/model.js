import mongoose, { Schema } from 'mongoose'

const anuncioSchema = new Schema({
    contenido: {
        type: String
    },
    ownerId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User'
    },
    comentarios: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Comentario'
    }]
}, {
    timestamps: true,
    toJSON: {
        virtuals: true,
        transform: (obj, ret) => { delete ret._id }
    }
})

anuncioSchema.methods = {
    view(full) {
        const view = {
            // simple view
            id: this.id,
            contenido: this.contenido,
            ownerId: this.ownerId,
            comentarios: this.comentarios,
            createdAt: this.createdAt,
            updatedAt: this.updatedAt
        }

        return full ? {
            ...view
            // add properties for a full view
        } : view
    }
}

const model = mongoose.model('Anuncio', anuncioSchema)

export const schema = model.schema
export default model