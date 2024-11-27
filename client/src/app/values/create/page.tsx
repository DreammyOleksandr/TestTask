'use client'

import { useState, useEffect } from 'react'
import { useRouter } from 'next/navigation'
import { fetchMetricTypes, fetchMetricValues } from '../../utils/api'
import { MetricType } from '@/app/interfaces/MetricType'
import { MetricValue } from '@/app/interfaces/MetricValue'

export default function CreateMetricValue() {
  const router = useRouter()
  const [metricTypes, setMetricTypes] = useState<MetricType[]>([])
  const [metricValues, setMetricValues] = useState<MetricValue[]>([])
  const [filteredRelations, setFilteredRelations] = useState<MetricValue[]>([])
  const [selectedMetricType, setSelectedMetricType] = useState<number | null>(null)
  const [value, setValue] = useState<string>('')
  const [selectedRelations, setSelectedRelations] = useState<number[]>([])

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [types, values] = await Promise.all([fetchMetricTypes(), fetchMetricValues()])
        setMetricTypes(types)
        setMetricValues(values)
      } catch (error) {
        console.error('Error fetching data:', error)
      }
    }
    fetchData()
  }, [])

  useEffect(() => {
    if (selectedMetricType !== null) {
      setFilteredRelations(
        metricValues.filter((relation) => relation.metricType.id !== selectedMetricType)
      )
    } else {
      setFilteredRelations([])
    }
  }, [selectedMetricType, metricValues])

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    if (!selectedMetricType) {
      alert('Please select a metric type')
      return
    }

    const body = {
      metricTypeId: selectedMetricType,
      value,
      relationIds: selectedRelations,
    }

    try {
      const response = await fetch('http://localhost:8080/metricvalues', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
      })
      if (!response.ok) throw new Error('Failed to create metric value')
      console.log('Metric value created successfully')
      router.push('/')
    } catch (error) {
      console.error('Error creating metric value:', error)
    }
  }

  const handleCheckboxChange = (id: number) => {
    setSelectedRelations((prev) =>
      prev.includes(id) ? prev.filter((relationId) => relationId !== id) : [...prev, id]
    )
  }

  return (
    <div className='container mt-5'>
      <h1>Create Metric Value</h1>
      <form onSubmit={handleSubmit}>
        <div className='mb-3'>
          <label className='form-label'>Metric Type</label>
          <select
            className='form-select'
            value={selectedMetricType || ''}
            onChange={(e) => setSelectedMetricType(Number(e.target.value))}
          >
            <option value='' disabled>
              Select a Metric Type
            </option>
            {metricTypes.map((type) => (
              <option key={type.id} value={type.id}>
                {type.name}
              </option>
            ))}
          </select>
        </div>
        <div className='mb-3'>
          <label className='form-label'>Value</label>
          <input
            type='text'
            className='form-control'
            value={value}
            onChange={(e) => setValue(e.target.value)}
            required
          />
        </div>
        <div className='mb-3'>
          <label className='form-label'>Relations</label>
          {filteredRelations.length === 0 && (
            <p className='text-muted'>Select a metric type to choose relations</p>
          )}
          {filteredRelations.length > 0 && (
            <table className='table table-striped'>
              <thead>
                <tr>
                  <th className='col-md-4'>#</th>
                  <th className='col-md-4'>Value</th>
                  <th className='col-md-4'>Recorded Date</th>
                  <th className='col-md-4'>Select</th>
                </tr>
              </thead>
              <tbody>
                {filteredRelations.map((relation, index) => (
                  <tr key={relation.id}>
                    <th scope='row'>{index + 1}</th>
                    <td>{relation.value}</td>
                    <td>{new Date(relation.recordedDate).toLocaleString()}</td>
                    <td>
                      <input
                        type='checkbox'
                        className='form-check-input'
                        id={`relation-${relation.id}`}
                        value={relation.id}
                        onChange={() => handleCheckboxChange(relation.id)}
                      />
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
        <button type='submit' className='btn btn-primary'>
          Create
        </button>
      </form>
    </div>
  )
}
