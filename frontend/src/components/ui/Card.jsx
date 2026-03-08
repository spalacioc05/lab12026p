import { cn } from '../../utils/cn'

export function Card({ children, className }) {
  return <div className={cn('glass rounded-2xl p-5 shadow-soft', className)}>{children}</div>
}
