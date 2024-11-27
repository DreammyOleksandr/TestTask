'use client'

import Link from 'next/link'
import { MetricValuesTableProps } from '@/app/interfaces/Props'

export const Table = ({ metricValues, onDelete }: MetricValuesTableProps) => (
  <table className='table mt-4'>
    <thead>
      <tr>
        <th>Value</th>
        <th>Recorded Date</th>
        <th>Metric Type Name</th>
        <th>Metric Type</th>
        <th>Actions</th>
      </tr>
    </thead>
    <tbody>
      {metricValues.map((metricValue) => (
        <tr key={metricValue.id}>
          <td>
            <Link href={`/values/details/${metricValue.id}`}>{metricValue.value}</Link>
          </td>
          <td>{new Date(metricValue.recordedDate).toLocaleString()}</td>
          <td>{metricValue.metricType.name}</td>
          <td>{metricValue.metricType.type}</td>
          <td>
            <div className='row'>
              <div className='col-md-6'>
                <Link
                  href={`/values/edit/${metricValue.id}`}
                  className='btn btn-warning btn-sm me-2 w-100'
                >
                  Edit
                </Link>
              </div>
              <div className='col-md-6'>
                <button
                  className='btn btn-danger btn-sm w-100'
                  onClick={() => onDelete(metricValue.id)}
                >
                  Delete
                </button>
              </div>
            </div>
          </td>
        </tr>
      ))}
    </tbody>
  </table>
)
