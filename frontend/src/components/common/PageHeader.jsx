import { motion } from 'framer-motion'

export function PageHeader({ title, description }) {
  return (
    <motion.div
      initial={{ opacity: 0, y: 12 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.35 }}
      className="mb-6"
    >
      <h1 className="font-display text-3xl font-bold tracking-tight text-ink md:text-4xl">{title}</h1>
      <p className="mt-2 max-w-3xl text-sm text-slate-600 md:text-base">{description}</p>
    </motion.div>
  )
}
