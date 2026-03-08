import { Link } from 'react-router-dom'

export function NotFoundPage() {
  return (
    <div className="flex min-h-screen flex-col items-center justify-center px-4 text-center">
      <p className="font-display text-7xl font-black text-brand-600">404</p>
      <h1 className="mt-3 font-display text-3xl font-bold text-ink">Pagina no encontrada</h1>
      <p className="mt-2 max-w-xl text-slate-600">La ruta solicitada no existe. Vuelve al panel principal para continuar.</p>
      <Link
        to="/customers"
        className="mt-6 rounded-xl bg-brand-600 px-5 py-2.5 text-sm font-semibold text-white transition hover:bg-brand-700"
      >
        Ir al inicio
      </Link>
    </div>
  )
}
