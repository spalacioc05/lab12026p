export function DataTable({ columns, rows, emptyLabel = 'Sin resultados' }) {
  if (!rows.length) {
    return <p className="rounded-xl border border-dashed border-slate-300 p-5 text-sm text-slate-500">{emptyLabel}</p>
  }

  return (
    <div className="overflow-x-auto">
      <table className="min-w-full border-separate border-spacing-y-2">
        <thead>
          <tr>
            {columns.map((column) => (
              <th key={column.key} className="px-4 py-2 text-left text-xs font-semibold uppercase tracking-wide text-slate-500">
                {column.header}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {rows.map((row, index) => (
            <tr key={row.id ?? index} className="rounded-xl bg-white shadow-sm">
              {columns.map((column) => (
                <td key={column.key} className="px-4 py-3 text-sm text-slate-700 first:rounded-l-xl last:rounded-r-xl">
                  {column.render ? column.render(row) : row[column.key]}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
